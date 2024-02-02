from datetime import MAXYEAR, datetime
from dates import days_until, is_this_week

def create_course_grade_matrix( data: list[list[str]] ) -> list[list[str]]:
    course_codes = set()

    for row in data:
        course_codes.add( row[0] )
        
        

    grade_matrix = [ [course, 0] for course in sorted( course_codes ) ]

    for course_code in grade_matrix:
        
        grades_earned = 0
        weighted_grades = 0 
        # sum all grades
        for row in data:
            if row[0] == course_code[0]:
                grades_earned += int( row[4] )
                weighted_grades += float( row[3] )
 
        # no grades earned so far
        if grades_earned == 0:
            course_code[1] = 0
        else:
            
            weighted_grade = grades_earned / weighted_grades * 100
            course_code[1] = weighted_grade
        
    for course in grade_matrix:
        course[1] = str( course[1] ) + "%"    

    return grade_matrix




def create_todo_list_matrix( data: list[list[str]] ) -> list[list[str]]:
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
        
        day_of_the_week = date.strftime("%a") # convert to string somehow    
        date_without_time = day_of_the_week+" - "+day+"/"+month+"/"+year


        data_for_row = [ course_code, assignment_name, date_without_time ]

        # no need to do it if its done
        if status == "complete" or status == "completed" or days_until(date) < 0: continue

        if ( priority == "none" or priority == "low" ) and is_this_week(date):
            assignments_to_do.append( data_for_row )

        if priority == "medium" and ( days_until(date) < 8 ):
            assignments_to_do.append( data_for_row )

        if ( priority == "high" or priority == "critical" ) and ( days_until(date) < 15 ):
            assignments_to_do.append( data_for_row )

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


def filter_matrix_by_status( matrix: list[list[str]], blacklisted_status: str ) -> list[list[str]]:
    
    filtered_matrix = []
    
    for assignment in matrix:
        assignment_status = assignment[5]
        
        if assignment_status.lower() == blacklisted_status.lower():
            continue
        
        filtered_matrix.append( assignment )
        
    
    return filtered_matrix