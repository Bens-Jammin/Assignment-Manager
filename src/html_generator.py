from html.entities import html5
from bs4 import BeautifulSoup
from colours import style_cell

def convert_matrix_to_html_table( table: list[list[str]], class_name: str, headers: list[str], all_centred=False ) -> str:
    
    html_table = "<table class='"+class_name+"'>"

    html_table += generate_header_code( headers )

    # rest of the data
    for row in table:
        
        # append all data from matrix to code
        html_table += "<tr>"
        
        selected_priority = row[-1]
        
        for i, cell in enumerate(row):

            colour_styling = style_cell( i, cell, all_centred )

            cell_contents = set_non_default_cell_contents( i, str(cell), selected_priority )

            cell_starter = "<td"+ colour_styling +">"
            cell_ender = "</td>"
            
            

            html_table += cell_starter+ cell_contents +cell_ender
        
        html_table += "</tr>"
    
    html_table += "</table>"

    return html_table


def generate_header_code( headers: list[str] ) -> str:
    header_code = "<tr>"
    for cell in headers:
        cell_starter = "<th style='text-align: center'>"
        cell_ender = "</th>"    
        header_code += cell_starter + str(cell) + cell_ender
    
    header_code += "</tr>"
    
    return header_code


def set_non_default_cell_contents( index: int, cell: str, selected_option ) -> str:
    cell_contents = ""
    # grade
    if index == 4:
        cell_contents = cell + "%"

    # status col index
    elif index == 5:
        options = [ "Not Started", "Partially Started", "Mostly Done", "Editing", "Complete" ]
        name = "status"
        dropdown_id = "status-dropdown"
        cell_contents = generate_dropdown( options, name, dropdown_id, selected_option="" )

    # priority col index
    elif index == 6:
        options = [ "None", "Low", "Medium", "High", "Critical" ]
        name = "priority"
        dropdown_id = "priority-dropdown"
        cell_contents = generate_dropdown( options, name, dropdown_id, selected_option )
        
    # default case, nothing to change
    else: cell_contents = cell
        
    return cell_contents


def generate_dropdown(options: list[str], name: str, id: str, selected_option: str) -> str:
    dropdown = "<form method='POST' class='dropdownbtn'>"
    
    select = "<select name='" + name + "' id='" + id + "' style='background-color: transparent; border: none;'>"

    for option in options:
        select += "<option value='" + option + "'"
        if option == selected_option:
            select += " selected"
        select += ">" + option + "</option>"
    
    select += "</select>"
    dropdown += select
    dropdown += "</form>"
    
    return dropdown
