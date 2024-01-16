import csv
from datetime import MAXYEAR, datetime, timedelta
from colours import style_cell

def convert_csv_to_matrix(file_path: str) -> list[list[str]]:
    with open(file_path, 'r') as file:
        line_count = sum(1 for line in file)

    
    # initialize matrix with room for headers and all the data 
    task_data = [ [] for i in range(line_count) ]

    # task_data[0] = headers

    with open(file_path, 'r') as file:
        reader = csv.reader(file)

        index = 0
        for row in reader:
            task_data[index] = row
            index += 1

    sorted_table = sort_matrix_by_date( task_data )
    return sorted_table


def create_grade_matrix( data: list[list[str]] ) -> list[list[str]]:
    course_codes = set()

    for row in data:
        course_codes.add( row[0] )

    grade_matrix = [ [course, 0] for course in course_codes ]

    for course_code in grade_matrix:
        for row in data:
            if row[0] == course_code:
                course_code[1] += int( row[4] )

    return grade_matrix



def days_until(target_date: datetime) -> int:

    current_date = datetime.now()

    days_difference = (target_date - current_date).days
    return days_difference


def is_this_week(target_date: datetime ) -> bool:

    current_date = datetime.now()

    start_of_week = current_date - timedelta(days=current_date.weekday())
    end_of_week = start_of_week + timedelta(days=6)

    return start_of_week <= target_date <= end_of_week


def create_todo_matrix( data: list[list[str]] ) -> list[list[str]]:
    assignments_to_do = []

    for row in data:

        date_str = row[2]
        if date_str == 'N/A':
            continue

        day,month,year = date_str.split(" ")[0].split("-")
        date = datetime( int(year), int(month), int(day)  )
        priority = row[-1].lower()

        course_code = row[0]
        assignment_name = row[1]

        if ( priority == "none" or priority == "low" ) and is_this_week(date):
            assignments_to_do.append( [course_code, assignment_name] )

        if priority == "medium" and ( days_until(date) < 8 ):
            assignments_to_do.append( [course_code, assignment_name] )

        if ( priority == "high" or priority == "critical" ) and ( days_until(date) < 15 ):
            assignments_to_do.append( [course_code, assignment_name] )

    return assignments_to_do





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
        for i in range(len(row)):
            cell = row[i]

            colour_styling = style_cell( i, cell )
            cell_starter = "<td"+ colour_styling +">"
            cell_ender = "</td>"

            html_table += cell_starter+ str(cell) +cell_ender
        
        html_table += "</tr>"
    
    html_table += "</table>"

    return html_table


def sort_matrix_by_date( matrix: list[list[str]] ) -> list[list[str]]:
    def date_key(row):
        date_str = row[2]
        if date_str == 'N/A':
            return datetime(MAXYEAR,1,1)
        else:
            day, month, year = date_str.split("-")
            return datetime(int(year), int(month), int(day))

    # Sort the matrix by date using the defined key function

    sorted_matrix = sorted( matrix[1:], key=date_key )

    return sorted_matrix
