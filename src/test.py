from flask import Flask, render_template
import data_manager as dm

app = Flask(__name__)



@app.route('/')
def test():
    
    # TODO : conditional font colouring
    # TODO : allow status, priority, grade to be changed
    # TODO : allow saving changes
    # TODO : add '%' to the grade columns

    table = dm.convert_csv_to_matrix("Ben.csv")

    assignments_headers = ["COURSE","NAME","DUE DATE","WEIGHT","GRADE","STATUS","PRIORITY"]
    html_table = dm.convert_matrix_to_html_table( table, 'assignment-table', assignments_headers )
    
    grade_table = dm.create_grade_matrix( table )
    grade_headers = ["COURSE", "GRADE"]
    html_grade_table = dm.convert_matrix_to_html_table( grade_table, 'grade-table', grade_headers )

    todo_table = dm.create_todo_matrix( table )
    todo_headers = ["COURSE", "ASSIGNMENT"]
    html_todo_table = dm.convert_matrix_to_html_table( todo_table, 'todo-table', todo_headers )


    return render_template('index.html', 
        assignments_table=html_table, 
        grade_table=html_grade_table, 
        todo_table=html_todo_table
    )

if __name__ == "__main__":
    app.run()