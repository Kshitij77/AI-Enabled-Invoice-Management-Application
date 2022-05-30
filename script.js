var add_submit = document.getElementById("addsub");
var left_btn = document.getElementById("left_pag_btn");
var right_btn = document.getElementById("right_pag_btn");
var off_set = 0;
var all_tr = document.querySelectorAll('tr');
const t_body = document.getElementById('t_body');

var custom_name = document.getElementById("cust_name");
var custom_num = document.getElementById("cust_num");
var invoic_num = document.getElementById("invoice_num");
var invoic_amt = document.getElementById("invoice_amt");
var due_date = document.getElementById("due");
var notes = document.getElementById("note");
var searchButton = document.getElementById("searchButton");
var searchtext = document.getElementById("searchtext");
//function myFunction() {
//  var x = document.getElementById("snackbar");
//  x.className = "show";
//}
//var snakspan = document.getElementsByClassName("snakclose")[0];
//snakspan.onclick = function() {
//	snackbar.style.display = "none";
//}


var add_modal = document.getElementById("addModal");
var btnadd = document.getElementById("addBtn");
var addspan = document.getElementsByClassName("addclose")[0];

btnadd.onclick = function() {
	add_modal.style.display = "block";
}
addspan.onclick = function() {
	add_modal.style.display = "none";
}

var edit_modal = document.getElementById("editModal");
var btnedit = document.getElementById("editBtn");
var editspan = document.getElementsByClassName("editclose")[0];
btnedit.onclick = function() {
	edit_modal.style.display = "block";
}
editspan.onclick = function() {
	edit_modal.style.display = "none";
}

var del_modal = document.getElementById("delModal");
var btndel = document.getElementById("delBtn");
var delspan = document.getElementsByClassName("delclose")[0];
btndel.onclick = function() {
	del_modal.style.display = "block";
}
delspan.onclick = function() {
	del_modal.style.display = "none";
}


const fetch_data = () => {
	fetch(`http://localhost:8080/Summer_Internship_Backend/dummyServlet?skip=${off_set}&search=false`, {
	})
		.then(response => response.json())
		.then(data => {
			console.log(data);
			buildrows(data);
		});
}

(async () => {
	fetch_data();
})();


const buildrows = (data) => {
	let html = '';
	for (let i = 0; i < data.length; i++) {
		let obj = data[i];
		html += `<tr>
            <th><input type="checkbox"></th> 
            <td>${obj.name}</td>
            <td>${obj.cust_no}</td>
            <td>${obj.invoice_id}</td>
            <td>${obj.amount}</td>
            <td>${obj.due_date}</td>
            <td>${obj.pred_date}</td>
            <td>-</td>
            </tr>`;
	}
	t_body.innerHTML = html;
}
searchButton.addEventListener('click',(e)=>{
	let search = searchtext.value;
	console.log(search);
	e.preventDefault();
	fetch(`http://localhost:8080/Summer_Internship_Backend/dummyServlet?search=true&invoiceId=${search}`)
	.then(res=>{
		console.log(res.json());
return	res.json();	
	})
	.then(resp=>{
		console.log(resp);
	buildrows(resp)	
	});
})
right_btn.addEventListener('click', (e) => {
	e.preventDefault();
	off_set += 10;
	fetch_data();

});
left_btn.addEventListener('click', (e) => {
	e.preventDefault();
	if (off_set != 0) {
		off_set -= 10;
		fetch_data();
	}

});


add_submit.addEventListener('click', (e) => {
	e.preventDefault();
	//console.log(custom_name.value);
	if(custom_name.value === "" || custom_num.value === "" || invoic_num.value === "" || invoic_amt.value === "" || due_date.value ===""){
		return;
	}
	fetch('http://localhost:8080/Summer_Internship_Backend/dummyServlet', {
		method: 'POST',
		body: JSON.stringify({
			cust_name: custom_name.value,
			cust_num: custom_num.value,
			invoice_num: invoic_num.value,
			invoice_amt: invoic_amt.value,
			due: due_date.value,
			note: notes.value

		}), headers: {
			"Accept": "application/json;"

		}
	}).then(res => console.log(res));
	add_modal.style.display = "none";

});


