$(window).on("load", function() {
	search();
	alert("welcome to our dashboard");

});


function save() {

	bookTitle = document.getElementById("title").value;
	authorName = document.getElementById("author").value;
	isbnNumber = document.getElementById("isbn").value;
	date = document.getElementById("dt").value;
	bookPrice = document.getElementById("price").value;
	genre = document.getElementById("genre").value;

	if (bookTitle == "" && authorName == "" && isbnNumber == "" && date == "" && bookPrice == "" && genre == "") {
		alert("fill all value")
	} else if (bookTitle == "") {
		alert("fill the booktitle")
	} else if (authorName == "") {
		alert("fill the authorName")
	} else if (isbnNumber == "" ) {
		alert("fill the isbnNumber")
	} else if(isbnNumber.length>15){
		alert("isbnNumber greater than fifteen")
	} else if (date == "") {
		alert("fill the date")
	} else if (bookPrice == "") {
		alert("fill the bookPrice")
	} else if (genre == "") {
		alert("fill the book genre name")
	} else {

		var allbooks = { "bookTitle": bookTitle, "authorName": authorName, "isbnNumber": isbnNumber, "date": date, "bookPrice": bookPrice, "genre": genre };


		$.ajax({
			url: "saveBooks",
			type: "POST",
			data: JSON.stringify(allbooks),
			contentType: "application/json; charset=utf-8",
			success: function() {
				clearsalltext();
				search();
				alert("success");
			}, error: function(error) {
				alert(error);
			}
		});

	}








}





function search() {


	bookTitle = document.getElementById("title").value;
	authorName = document.getElementById("author").value;
	isbnNumber = document.getElementById("isbn").value;
	date = document.getElementById("dt").value;
	bookPrice = document.getElementById("price").value;
	genre = document.getElementById("genre").value;

	var book = { "bookTitle": bookTitle, "authorName": authorName, "isbnNumber": isbnNumber, "date": date, "bookPrice": bookPrice, "genre": genre };


	$.ajax({
		url: "searchBook",
		type: "POST",
		data: JSON.stringify(book),
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			var book = data;


			createTableFrom(book);

		}, error: function(error) {
			alert(error);
		}
	});





}

function deleteBookRecord(bookIsbnNumber) {

	event.stopPropagation();

	$.ajax({
		url: "deleteBook",
		type: "POST",
		data: JSON.stringify(bookIsbnNumber),
		contentType: "application/json; charset=utf-8",
		success: function(data) {
			if (data == 1) {

				alert("delete success");
				search();
			}




		}, error: function(error) {
			alert(error);
		}
	});




}

function createTableFrom(booksData) {



	var table = document.getElementById("table");

	var rowCount = table.rows.length;

	for (var i = rowCount - 1; i > 0; i--) {
		table.deleteRow(i);
	}
	for (var i = 0; i < booksData.length; i++) {

		tr = table.insertRow(-1);

		var tabCell = tr.insertCell(-1);
		tabCell.innerHTML = booksData[i].title

		var tabCell = tr.insertCell(-1);
		tabCell.innerHTML = booksData[i].author
		var tabCell = tr.insertCell(-1);
		tabCell.innerHTML = booksData[i].isbn
		var tabCell = tr.insertCell(-1);
		tabCell.innerHTML = booksData[i].publication_Date
		var tabCell = tr.insertCell(-1);
		tabCell.innerHTML = booksData[i].price
		var tabCell = tr.insertCell(-1);
		tabCell.innerHTML = booksData[i].genre

		var tabCell = tr.insertCell(-1);
		tabCell.innerHTML = '<button class="btn btn-primary btn-xs my-xs-btn" type="button" onClick="deleteBookRecord(' + booksData[i].isbn + ')" >'
			+ ' Delete</button>';


	}

	var table = document.getElementById("table");

	var rows = table.getElementsByTagName("tr");

	for (i = 0; i < rows.length; i++) {

		row = table.rows[i];

		row.onclick = function() {

			var cell = this.getElementsByTagName("td")[0];
			var cell1 = this.getElementsByTagName("td")[1];
			var cell2 = this.getElementsByTagName("td")[2];
			var cell3 = this.getElementsByTagName("td")[3];
			var cell4 = this.getElementsByTagName("td")[4];
			var cell5 = this.getElementsByTagName("td")[5];




			document.getElementById("title").value = cell.innerHTML
			document.getElementById("author").value = cell1.innerHTML
			document.getElementById("isbn").value = cell2.innerHTML
			document.getElementById("dt").value = cell3.innerHTML
			document.getElementById("price").value = cell4.innerHTML
			document.getElementById("genre").value = cell5.innerHTML


		};
	}








}

function clearsalltext() {

	            document.getElementById('title').value = "";
				document.getElementById('author').value = "";
				document.getElementById('isbn').value = "";
				document.getElementById('dt').value = "";
				document.getElementById('price').value = "";
				document.getElementById('genre').value = "";
}




































