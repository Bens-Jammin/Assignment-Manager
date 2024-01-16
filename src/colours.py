
course_code_to_colour = {
    "CSI-2132": "#6CD4FF",
    "CSI-2101": "#8B80F9",
    "CSI-2120": "#CFBFF7",
    "CSI-2911": "#CFB1B7",
    "MAT-2377": "#83858C",
}

def style_course_code( course_code: str) -> str:
    try:
        hex_colour = course_code_to_colour[course_code]
    except:
        return ""
    
    return " style=background-color:"+hex_colour


def style_grade( grade: str ) -> str:
    try:
        grade = int(grade)

        hex_colour = "#ff0000"
        if grade > 70: hex_colour = "#cc7a00"
        if grade > 80: hex_colour = "#cccc00" 
        if grade > 90: hex_colour = "#30a807"

        return " style=background-color:"+hex_colour
    
    except:
        return ""
    


def style_priority( priority: str ) -> str:
    priority = priority.lower()

    if priority == "low": return ""
    if priority == "medium": return ""
    if priority == "high": return ""
    if priority == "critical": return ""
    
    return ""


def style_status( status: str ) -> str:
    status = status.lower()

    if status == "not started": return ""
    if status == "started": return ""
    if status == "editing": return ""
    if status == "done": return ""

    return ""



def style_cell( index: int, cell: str ) -> str:

    if index == 0: return style_course_code( cell )
    if index == 1: return "" 
    if index == 2: return ""
    if index == 3: return ""
    if index == 4: return style_grade( cell )
    if index == 5: return style_priority( cell )
    if index == 6: return style_status( cell )

    # if index cant be found, returns
    # an empty string bc None + str = error !!
    return ""