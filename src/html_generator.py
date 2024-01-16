from data_manager import style_cell

def convert_matrix_to_html_table( table: list[list[str]], class_name: str, headers: list[str] ) -> str:
    
    html_table = "<table class='"+class_name+"'>"

    # headers
    html_table += "<tr>"
    for cell in headers:
        cell_starter = "<th style='text-align: center'>"
        cell_ender = "</th>"
        html_table += cell_starter + str(cell) + cell_ender
    html_table += "</tr>"

    # rest of the data
    for row in table:
        
        # append all data from matrix to code
        html_table += "<tr>"
        for i, cell in enumerate(row):

            colour_styling = style_cell( i, cell )

            cell_contents = str(cell)

            # status col index
            if i == 5:
                options = [ "Not Started", "Partially Started", "Mostly Done", "Editing", "Complete" ]
                name = "status"
                dropdown_id = "status-dropdown"
                cell_contents = generate_dropdown( options, name, dropdown_id )

            # priority col index
            elif i == 6:
                options = [ "None", "Low", "Medium", "High", "Critical" ]
                name = "priority"
                dropdown_id = "priority-dropdown"
                cell_contents = generate_dropdown( options, name, dropdown_id )

            cell_starter = "<td"+ colour_styling +">"
            cell_ender = "</td>"

            html_table += cell_starter+ cell_contents +cell_ender
        
        html_table += "</tr>"
    
    html_table += "</table>"

    return html_table



def generate_dropdown( options: list[str], name: str, id: str) -> str:

    dropdown = "<form method='post'><select name='"+name+"' id='"+id+"'>"

    for i, option in enumerate(options):
        dropdown += "<option value='option"+str(i+1)+"'>"+option+"</option>"

    dropdown += "</select></form>"

    return dropdown
