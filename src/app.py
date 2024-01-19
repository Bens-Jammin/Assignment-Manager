from flask import Flask, render_template, jsonify
import data_manager as dm
import html_generator as html
import table_generator as tgen

app = Flask(__name__)


@app.route('/save', methods=['POST'])
def save():
    print("saving...")
    
    return jsonify({'status': 'success'}), 200 


@app.route('/')
def app_code():
    
    # TODO : allow saving changes
    # TODO : set default selected option for dropdown

    save()

    table = dm.convert_csv_to_matrix("data\Ben\Winter 2024\Ben.csv")

    assignments_headers = ["COURSE","NAME","DUE DATE","WEIGHT","GRADE","STATUS","PRIORITY"]
    html_table = html.convert_matrix_to_html_table( table, 'assignment-table', assignments_headers )
    
    grade_table = tgen.create_grade_matrix( table )
    grade_headers = ["COURSE", "GRADE"]
    html_grade_table = html.convert_matrix_to_html_table( grade_table, 'grade-table', grade_headers )

    todo_table = tgen.create_todo_matrix( table )
    todo_headers = ["COURSE", "ASSIGNMENT"]
    html_todo_table = html.convert_matrix_to_html_table( todo_table, 'todo-table', todo_headers )

    priority_dropdown_options = ["Low", "Medium", "High", "Critical"]

    return render_template('index.html', 
        assignments_table=html_table, 
        grade_table=html_grade_table, 
        todo_table=html_todo_table,
        options = priority_dropdown_options
    )
    
    
    
@app.route('/timetable')
def timetable():
   
   json_data = dm.convert_json_to_timetable("data\Ben\Winter 2024\Ben.json")
   timetable = html.generate_time_table(json_data)
   
   return render_template('timetable.html', time_table = timetable)
  

if __name__ == "__main__":
    app.run(debug=True)