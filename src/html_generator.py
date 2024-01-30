from colours import style_cell

def convert_matrix_to_html_table( table: list[list[str]], class_name: str, headers: list[str], center_all=False ) -> str:
    
    html_table = "<table class='"+class_name+"' id='"+class_name+"'>"
    html_table += generate_html_header_tags( headers )

    for row in table:
        
        # append all data from matrix to code
        html_table += "<tr>"
        
        selected_priority = row[-1]
        selected_status = row[-2]
        
        for i, cell in enumerate(row):

            colour_styling = style_cell( i, cell, center_all )

            # selected_priority is in col 6 , selected_status is in col 5              
            selected_option = selected_priority if i == 6 else selected_status

            cell_contents = set_non_default_cell_contents( i, str(cell), selected_option )

            cell_starter = "<td"+ colour_styling +">"
            cell_ender = "</td>"

            html_table += cell_starter+ cell_contents +cell_ender
        
        
        html_table += "</tr>"
    
    
    html_table += "</table>"

    return html_table


def generate_html_header_tags( headers: list[str] ) -> str:
    header_code = "<tr>"
    for cell in headers:
        cell_starter = "<th style='text-align: center'>"
        cell_ender = "</th>"    
        header_code += cell_starter + str(cell) + cell_ender
    
    header_code += "</tr>"
    
    return header_code


def set_non_default_cell_contents( index: int, cell: str, selected_option: str ) -> str:
    """ generates non-text html content for a cell

    Args:
        index (int): column index of the cell
        cell (str): content of the cell
        selected_option (str): default dropdown option

    Returns:
        formatted cell content to be rendered in the table
    """
    cell_contents = ""
    
    if index == 4:    # grade
        cell_contents = cell + "%"
    elif index == 5:    # status col index
        options = [ "Not Started", "Partially Started", "Mostly Done", "Editing", "Complete" ]
        name = "status"
        dropdown_id = "status-dropdown"
        cell_contents = generate_dropdown( options, name, dropdown_id, selected_option )
    elif index == 6:    # priority col index
        options = [ "None", "Low", "Medium", "High", "Critical" ]
        name = "priority"
        dropdown_id = "priority-dropdown"
        cell_contents = generate_dropdown( options, name, dropdown_id, selected_option )
        
    # default case, nothing to change
    else: cell_contents = cell
        
    return cell_contents


def generate_dropdown(options: list[str], name: str, id: str, selected_option: str) -> str:
    dropdown = "<div class='dropdown-wrapper' ><form method='POST' class='dropdownbtn'>"
    
    select = "<select name='" + name + "' id='" + id + "' style='background-color: transparent; border: none;'>"

    for option in options:
        select += "<option value='" + option + "'"
        if option.lower() == selected_option.lower():
            select += " selected"
        select += ">" + option + "</option>"
    
    select += "</select>"
    dropdown += select
    dropdown += "</form></div>"
    
    return dropdown



def generate_time_table(data: list[str]) -> str:
    html_table = "<table class='time-table' id='time-table'>"
    html_table += generate_html_header_tags(["Times","Monday", "Tuesday", "Wednesday", "Thursday", "Friday"])

    cols = 6  # Assume there are 6 columns (5 week days + time block column)
    rows = len(data) // cols  

    times = ["8:30", "10:00", "11:30", "13:00", "14:30", "16:00", "17:30", "19:00", "20:30"]

    for i in range(rows):
        html_table += "<tr>"
        for j in range(cols):
            
            if j == 0:  # j = 0 means to generate time block column
                cell_data = times[i]
            else:
                cell_index = 5*i + (j-1)
                cell_data = data[ cell_index ]
            
            cell = "<td style='text-align: center'>" + cell_data + "</td>"
            html_table += cell
            
        html_table += "</tr>"

    html_table += "</table>"

    return html_table