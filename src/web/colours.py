
course_colour_map = {
    "CSI-2101": "#6CD4FF",
    "CSI-2911": "#8B80F9",
    "MAT-2377": "#CFBFF7",
    "CSI-2132": "#CFB1B7",
    "CSI-2120": "#83858C"
}


def style_course_code( course_code: str) -> str:
    colour = course_colour_map.get( course_code )

    if colour == None:
        return ""

    styling = "style=color:'"+colour+"'"
    return styling