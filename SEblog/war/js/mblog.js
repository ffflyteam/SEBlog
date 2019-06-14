$(document).ready(function(){
  // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
  $('.modal').modal();
});
 $('#modal1').modal('open');
 $('#modal2').modal({
      dismissible: true, // Modal can be dismissed by clicking outside of the modal
      opacity: .5, // Opacity of modal background
      in_duration: 300, // Transition in duration
      out_duration: 200, // Transition out duration
      starting_top: '20%', // Starting top style attribute
      ending_top: '30%', // Ending top style attribute
      ready: function(modal, trigger) { // Callback for Modal open. Modal and trigger parameters available.
        alert("Ready");
        console.log(modal, trigger);
      },
      complete: function() { alert('Closed'); } // Callback for Modal close
    }
  );

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

 //设置切换博客内容
var spans = document.getElementById('blogSwitch').getElementsByTagName("span");
 function spansClick() {
  var blogList = document.getElementById('mycontainer').children;
    for(var i=0;i < spans.length;i++){
      spans[i].setAttribute("class","");
      blogList[i+2].style.display = "none";
    }
    this.setAttribute("class","selected-span");
    var index = this.getAttribute("index");
    blogList[index].style.display = "block";
 }
 function setBlogSwitch(){
  for(var i=0;i < spans.length;i++){
    spans[i].setAttribute("index",i+2);
    spans[i].onclick = spansClick;
  }
 }
 setBlogSwitch();