RED = "#e07070"
ORANGE = "#F78C6B"
YELLOW = "#FFD166"
GREEN = "#83D483"
TURQUOISE = "#06D6A0"
LIGHT_BLUE = "#0CB0A9"
DARK_BLUE = "#118AB2"
NAVY = "#073B4C"


course_code_to_colour = {
    "CSI-2132": DARK_BLUE,
    "CSI-2101": LIGHT_BLUE,
    "CSI-2120": TURQUOISE,
    "CSI-2911": GREEN,
    "MAT-2377": YELLOW,
}


def style_course_code( course_code: str) -> str:
    try:
        hex_colour = course_code_to_colour[course_code]
    except:
        return ""
    
    return hex_colour


def style_grade( grade: str ) -> str:
    try:
        grade = int(grade)

        hex_colour = RED
        if grade > 70: hex_colour = ORANGE # "#cc7a00"
        if grade > 80: hex_colour = YELLOW # "#cccc00" 
        if grade > 90: hex_colour = GREEN  # "#30a807"

        return hex_colour
    
    except:
        return ""
    


def style_priority( priority: str ) -> str:
    priority = priority.lower().strip()

    if priority == "low":      return LIGHT_BLUE
    if priority == "medium":   return GREEN
    if priority == "high":     return YELLOW
    if priority == "critical": return ORANGE
    
    return ""


def style_status( status: str ) -> str:
    status = status.lower()
    if status == "not started": return RED
    if status == "started":     return ORANGE
    if status == "editing":     return TURQUOISE
    if status == "done":        return GREEN

    return ""



def style_cell( index: int, cell: str, is_centred: bool ) -> str:

    if index == 0: colour = "" # style_course_code( cell )
    if index == 1: colour = "" 
    if index == 2: colour = ""
    if index == 3: colour = ""
    if index == 4: colour = style_grade( cell )
    if index == 5: colour = style_status( cell )
    if index == 6: colour = style_priority( cell )

    alignment = "'text-align: center'"

    if colour == "" and not is_centred: return ""
    if is_centred and colour == "":     return " style="+alignment
    if colour != "" and not is_centred: return " style=background-color:"+colour
    return " style=background-color:"+colour+ "; "+alignment