import csv
from table_generator import sort_matrix_by_date
import json


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


def convert_json_to_timetable(file_path: str) -> list[str]:
    with open(file_path, 'r') as file:
        data = json.load(file)
        
    schedule = []
    times = list(data.keys())
    days = list(data[times[0]].keys())
    
    for i, time in enumerate(times):
        for j, day in enumerate(days):
            schedule.append(data[time][day])
        
    return schedule



def save_to_csv( file_path: str, data: list[list[str]] ) -> None:
    print("NOT IMPLEMENTED YET!!!!!!!!")
