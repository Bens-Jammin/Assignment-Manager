import { initializeApp } from 'firebase/app';
import { getFirestore, collection, getDocs } from 'firebase/firestore/lite';


let saveButton = document.getElementById("save-button").addEventListener('click', function(){

    const firebaseConfig = {
        apiKey: "AIzaSyD-tYJw0jCDgIYK9jwbjBfguGXj8NY7hNY",
        authDomain: "assignment-tracker-3cf0e.firebaseapp.com",
        databaseURL: "https://assignment-tracker-3cf0e-default-rtdb.firebaseio.com",
        projectId: "assignment-tracker-3cf0e",
        storageBucket: "assignment-tracker-3cf0e.appspot.com",
        messagingSenderId: "703233757167",
        appId: "1:703233757167:web:18847f84c11ac68be0701a",
        measurementId: "G-FMTBZQZR64"
    };
    const app = initializeApp(firebaseConfig);
    const db = getFirestore(app);


    let table = document.getElementById("assignment-table");
    for (let i = 0, row; row = table.rows[i]; i++) {
        
        let courseCode = row.cells[0].innerText;
        let assignmentName = row.cells[1].innerText;
        let dueDate = row.cells[2].innerText;
        let weight = row.cells[3].innerText;
        let grade = row.cells[4].innerText;

        let status_dropdown = document.getElementById("status-dropdown");
        let status = status_dropdown.options[status_dropdown.selectedIndex].value;
        
        let priority_dropdown = document.getElementById("priority-dropdown");
        let priority = priority_dropdown.options[priority_dropdown.selectedIndex].value;
    
        let id = courseCode+"_"+assignmentName;
        let dataPath = '/assignments/'+id;

        let dataReference = ref(db, dataPath);
        set(dataReference, {
            ID : id,
            Course : courseCode,
            Name : assignmentName,
            DueDate : dueDate,
            Weight : weight,
            Grade : grade,
            Status : status,
            priority : priority 
        }).then(() => {
            console.log("Data saved successfully.");
            alert("Data saved successfully!");
        }).catch((error) => {
            console.error("Error saving data: ", error);
        });
    }
});