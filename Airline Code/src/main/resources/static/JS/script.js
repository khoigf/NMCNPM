console.log("This is Script File");

const toggleSidebar = ()=>{
   var t=document.getElementById("sid");
   var m=document.getElementById("content");
   
   if(t.style.display == 'block'){
    t.style.display='none';
    m.style.marginLeft="0%"; 
   }else{
         t.style.display='block';
         m.style.marginLeft= "20%";  
         
   }
   
};

//searching
const search =() =>{
//   console.log("Searching....");
let query=document.getElementById("search-input").value;
let st=document.getElementById("search-result");
if (query==='') {
      st.style.display='none'   
}else{
   //search 

//    sending request to server
   let url=`http://localhost:1200/search/${query}`;

     fetch(url).then((response) =>{
      return response.json();
     }).then((data) =>{
          
          let text=`<div class='list-group'>`;
                  data.forEach((addFlight) => {
                     text+=  `<a href='/admin/${addFlight.flightNo}/flight' class='list-group-item list-group-item-action'> ${addFlight.flightName},${addFlight.flightNo}  </a>` 
                  });
          text+=`</div>`;
          st.innerHTML=text;
          st.style.display='block';
     });
   
   
   //console.log(query);
}
const searchu =() =>{
   //   console.log("Searching....");
   let query1=document.getElementById("searchu-input").value;
   let st1=document.getElementById("searchu-result");
   if (query1==='') {
         st1.style.display='none'   
   }else{
      //search 
   
   //    sending request to server
      let url=`http://localhost:1200/searchu/${query1}`;
   
        fetch(url).then((response) =>{
         return response.json();
        }).then((data) =>{
             
             let text1=`<div class='list-group'>`;
                     data.forEach((addUser) => {
                        text1+=  `<a href='/admin/${addUser.email}/user' class='list-group-item list-group-item-action'> ${addUser.name},${addUser.email}  </a>` 
                     });
             text1+=`</div>`;
             st1.innerHTML=text1;
             st1.style.display='block';
        });
      
      
      //console.log(query);
   }
   
   }
}
//seat store
const addSeat = () =>{

   var itemForm = document.getElementById('plane'); // getting the parent container of all the checkbox inputs
   var checkBoxes = itemForm.querySelectorAll('input[type="checkbox"]');

   let result = [];
   checkBoxes.forEach(item => { // loop all the checkbox item
       if (item.checked) {  //if the check box is checked
           let data = {    // create an object
               item: item.value,
               selected: item.checked
           }
           result.push(data); //stored the objects to result array
       }
   })

var url=`http://localhost:1200/user/b_seat`;

fetch(url, {
   method: 'POST', // or 'PUT'
   body: JSON.stringify(result), // data can be `string` or {object}!
   headers:{
     'Content-Type': 'application/json'
   }

 }).then((res) => {
    
   res.json();
})
 .then((response) => {
    console.log('Success:', JSON.stringify(response));
    result=response;
})
 
result.forEach((data)=>{
   document.getElementById(data.item).setAttribute("disabled",'');
});

let seat_select=document.getElementById("seat_select");
if(seat_select.checked){
console.log("Data");
   document.getElementById("book").style.display="inline";
}

}

const showReadio = () =>{
	var itemForm = document.getElementById('plane');
	var checkBoxes = itemForm.querySelectorAll('input[type="checkbox"]');
	checkBoxes.forEach(item => { // loop all the checkbox item
	       if (item.checked) {  //if the check box is checked
	           document.getElementById("seat_select").disabled=false;
	       }
	})
}

//flight Cancle and Start form submit
const flightCancle12 =(fNo,cp,onof)=>{
	var fm=document.getElementById("myFlight");
	fm.action=`/admin/flightOnOf/${fNo}/${cp}/${onof}`;
    fm.submit();
}

const userenable12 =(femail,cp,onof)=>{
	var fm=document.getElementById("activityUser");
	fm.action=`/admin/userOnOf/${femail}/${cp}/${onof}`;
    fm.submit();
}

//seat disable
function clicktoSee1(){
document.getElementById("liston").style.display="inline"
console.log("Hellllo");
}




