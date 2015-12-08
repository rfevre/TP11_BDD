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
		req.open("GET", "./servlet/selectMax", true);
		req.onreadystatechange = actualiserPage;
		req.send(null);
	}

	function actualiserPage(){
		if (req.readyState ==4)
				if (req.status == 200)
				{
					var data = req.responseXML;
					var prix = data.getElementsByTagName("prix")[0].firstChild;
					var libelle = data.getElementsByTagName("libelle")[0].firstChild;
					var pno = data.getElementsByTagName("pno")[0].firstChild;
					document.getElementById("prix").value = prix.nodeValue;
					document.getElementById("libelle").value = libelle.nodeValue;
					document.getElementById("pno").value = pno.nodeValue;
				}
			else alert("erreur : "+req.status);
		}

	</script>

	<%! int cpt=0; %>
	<% cpt++; %>
</head>
<body>

	<center>
		<form>
			<h1>Récupérer le SELECT MAX</h1>
			<h2>Compteur = <%= cpt %> </h2>
			<input type="text" value="prix" id="prix" />
			<input type="text" value="libelle" id="libelle" />
			<input type="text" value="pno" id="pno" />
			<p/>
		    <input type="button" value="Valider" onclick="cliquer()" />
	    </form>
	</center>

</body>
</html>