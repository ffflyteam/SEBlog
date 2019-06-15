$('.dropdown-button').dropdown({
      inDuration: 300,
      outDuration: 225,
      constrain_width: false, // Does not change width of dropdown to that of the activator
      hover: true, // Activate on hover
      gutter: 0, // Spacing from edge
      belowOrigin: true, // Displays dropdown below the button
      alignment: 'left' // Displays dropdown with edge aligned to the left of button
    }
  );

var spans = document.getElementById('mune').children;
var uls = document.getElementById('blogContainer').children;
for (var i = 0; i < spans.length; i++) {
	spans[i].setAttribute("index",i);
	spans[i].onclick = change;
}
function change(){
	for (var i = 0; i < uls.length; i++) {
		uls[i].style.display = "none";
	}
	for (var i = 0; i < spans.length; i++) {
		spans[i].className = "";
	}
	this.className = "selected-span";
	var index = this.getAttribute("index");
	uls[index].style.display = "block";
}