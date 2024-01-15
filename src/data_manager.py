import csv
import datetime

def convert_csv_to_matrix(file_path: str) -> list[list[str]]:
    try:
        with open(file_path, 'r') as file:
            line_count = sum(1 for line in file)

        headers = [
            "COURSE",
            "NAME",
            "DUE DATE",
            "WEIGHT",
            "GRADE",
            "STATUS",
            "PRIORITY"
        ]
        
        # initialize matrix with room for headers and all the data 
        task_data = [ [] for i in range(line_count+1) ]

        task_data[0] = headers

        with open(file_path, 'r') as file:
            reader = csv.reader(file)

            index = 1
            for row in reader:
                task_data[index] = row
                index += 1

        sorted_table = sort_matrix_by_date( task_data )

        return sorted_table
    except Exception as e:
        print(e)



def convert_matrix_to_html_table( table: list[list[str]] ) -> str:
    
    html_table = "<table border=2, class='assignment-table'>"

    is_first_row = True

    for row in table:
        cell_starter = "<td>"
        cell_ender = "</td>"
        if is_first_row:
            row_starter = "<th>"
            row_ender = "</th>"
            is_first_row = False
        
        # append all data from matrix to code
        html_table += "<tr>"
        for cell in row:
            html_table += cell_starter+cell+cell_ender
        
        html_table += "</tr>"
    
    html_table += "</table>"

    return html_table


def sort_matrix_by_date( matrix: list[list[str]] ) -> list[list[str]]:
    def date_key(row):
        date_str = row[2]
        if date_str == 'N/A':
            return datetime.max
        else:
            return datetime.strptime(date_str, "%d-%m-%Y")

    # Sort the matrix by date using the defined key function
    sorted_matrix = sorted(matrix[1:], key=date_key() )

    # Reattach the header row
    return [matrix[0]] + sorted_matrix


for row in convert_csv_to_matrix( "Ben.csv" ):
    print(row)