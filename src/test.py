from flask import Flask, render_template
import data_manager as dm

app = Flask(__name__)



@app.route('/')
def test():
    
    table = dm.convert_csv_to_matrix("Ben.csv")
    html_table = dm.convert_matrix_to_html_table( table )
    
    return render_template('index.html', table=html_table)


if __name__ == "__main__":
    app.run()