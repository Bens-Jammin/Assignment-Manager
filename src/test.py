from flask import Flask, render_template
import data_manager as dm

app = Flask(__name__)



@app.route('/')
def test():
    
    table = dm.convert_csv_to_matrix("Ben.csv")
    html_table = dm.convert_matrix_to_html_table( table, 'assignment-table' )
    
    grade_table = dm.create_grade_matrix( table )
    html_grade_table = dm.convert_matrix_to_html_table( grade_table, 'grade-table' )

    todo_table = dm.create_todo_matrix( table )
    html_todo_table = dm.convert_matrix_to_html_table( todo_table, 'todo-table' )

    for row in todo_table:
        print(row)
    # how do i return both tables ?

    return render_template('index.html', assignments_table=html_table, grade_table=html_grade_table, todo_table=html_todo_table)

if __name__ == "__main__":
    app.run()