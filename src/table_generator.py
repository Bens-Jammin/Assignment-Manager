from datetime import MAXYEAR, datetime
from dates import days_until, is_this_week

def create_grade_matrix( data: list[list[str]] ) -> list[list[str]]:
    course_codes = set()

    for row in data:
        course_codes.add( row[0] )
        
        

    grade_matrix = [ [course, 0] for course in sorted( course_codes ) ]

    for course_code in grade_matrix:
        # sum all grades
        for row in data:
            if row[0] == course_code:   # weighted grade = grade/weight
                course_code[1] += int( row[4] ) / int( row[3] )
        
    for course in grade_matrix:
        course[1] = str( course[1] ) + "%"    

    return grade_matrix




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
        status = row[-2].lower()

        # no need to do it if its done
        if status == "complete" or status == "completed": continue

        if ( priority == "none" or priority == "low" ) and is_this_week(date):
            assignments_to_do.append( [course_code, assignment_name] )

        if priority == "medium" and ( days_until(date) < 8 ):
            assignments_to_do.append( [course_code, assignment_name] )

        if ( priority == "high" or priority == "critical" ) and ( days_until(date) < 15 ):
            assignments_to_do.append( [course_code, assignment_name] )

    return assignments_to_do




def sort_matrix_by_date( matrix: list[list[str]] ) -> list[list[str]]:
    
    def date_key(row):
        date_str = row[2]
        if date_str == 'N/A':
            return datetime(MAXYEAR,1,1)
        else:
            day, month, year = date_str.split("-")
            return datetime(int(year), int(month), int(day))

    
    sorted_matrix = sorted( matrix[1:], key=date_key )

    return sorted_matrix
