// validate search form to make sure keyword is not empty
function validateSearch(form){

  // return false if search query is empty
  if(form.txtKeyword.value == ""){
    alert("Search keyword is required");
    return false;
  }

  return true;
}

// validate the quantity entered by user
function validateQuantity(form){

  // return false if quantity is empty
  if(form.qty.value == ""){
    alert("Quantity is required");
    return false;
  }

  // check if quantity is an not an integer and display an error
  if(!isInteger(form.qty.value)){
	alert("Quantity should be an integrer greater than zero");
	return false;
  }
  
  // Check if quantity is not less than 1
  if(form.qty.value < 1){
	alert("Quantity should be greater than zero");
	return false;
  }
  return true;
}

// when user changes sort by selection box or items per page to refresh page
function search(){
  document.getElementById("frmProducts").submit();
}
// check if a radio button in the product list is selected, otherwise display an error
function validateSelectProduct(form){
	for (i = 0; i <form.radId.length; i++) {
  	if (form.radId[i].checked) {
		  return true;
  	}
	}
	alert("Please select a fruit from the list");
	return false;
}


// This function is taken from: http://acmesoffware.com/acme/default.asp
function isInteger (s)
{
  var i;
  if (isEmpty(s))
  if (isInteger.arguments.length == 1) return 0;
  else return (isInteger.arguments[1] == true);
  for (i = 0; i < s.length; i++)
  {
	 var c = s.charAt(i);
	 if (!isDigit(c)) return false;
  }
  return true;
}

function isEmpty(s){
  return ((s == null) || (s.length == 0))
}

function isDigit (c)
{
  return ((c >= "0") && (c <= "9"))
}

// Validate login form
function loginvalidate(form)
{
  // check if username is not blank
  if(form.txtUsername.value == "")
  {
    alert("Username is required!");
    form.txtUsername.focus();
    return false;
  }

  // check if password is not blank
  if(form.txtPassword.value == "")
  {
    alert("Password is required!");
    form.txtPassword.focus();
    return false;
  }
  return true;
}


// Validate registration form
function registerValidate(form)
{

  // regular expression to check name
  re=/^[a-z A-Z]+$/

  // validate username
  if(!re.test(form.txtUsername.value))
  {
    alert("Username is not valid!");
    form.txtUsername.focus();
    return false;
  }

  // validate password
  if(form.txtPassword.value == "")
  {
    alert("Password is required!");
    form.txtPassword.focus();
    return false;
  }

  // validate password
  if(form.txtPassword.value != form.txtConfirm.value)
  {
    alert("Password and confirm did not match!");
    form.txtPassword.focus();
    return false;
  }

  // return fasle when name contains none characters
  if(!re.test(form.txtName.value)){
    alert("Full name is not valid");
    form.txtName.focus();
    return false;
  }

  // regular expression to check email address
  re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
  // return false if email address is not valid
  if(!re.test(form.txtEmail.value))
  {
    alert("Email address is not valid!");
    form.txtEmail.focus();
    return false;
  }
	
  return true;
}
