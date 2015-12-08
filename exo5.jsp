<!DOCTYPE html>	
<html>
<head>
   <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <%@ page import="java.util.*" %>
   <%@ page contentType="text/html; charset=UTF-8" %>
   <TITLE>Page AJAX</TITLE>

   <script type ="text/javascript">
   var req = init();
   function init() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}

	function cliquer() {
		req.open("GET", "./servlet/select", true);
		req.onreadystatechange = actualiserPage;
		req.send(null);
	}

	function actualiserPage(){
		if (req.readyState ==4)
				if (req.status == 200)
				{
					var reponse = req.responseText;
					var monElmt = document.getElementById("textArea");
					monElmt.value = reponse;
				}
			else alert("erreur : "+req.status);
		}

	setInterval(cliquer,500);
	</script>

	<%! int cpt=0; %>
	<% cpt++; %>
</head>
<body>

	<center>
		<form>
			<h1>Récupérer contenu fichier</h1>
			<h2>Compteur = <%= cpt %> </h2>
			<textarea rows="4" cols="50" id="textArea"> </textarea>
			<p/>
		    <input type="button" value="Valider" onclick="cliquer()" />
	    </form>
	</center>

</body>
</html>