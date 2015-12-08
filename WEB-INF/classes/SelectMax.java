import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.Properties;

@WebServlet("/servlet/selectMax")
public class SelectMax extends HttpServlet
{
    public void service( HttpServletRequest req, HttpServletResponse res ) 
	throws ServletException, IOException
    {
	PrintWriter out = res.getWriter();
    
    Connection con = null;
	Statement stmt = null;
    ResultSet rs = null;

	HttpSession session = req.getSession(true);
	
	String url = "";
	String nom = "";
	String mdp = "";

	try {
	    //Enregistrement du Driver
	    Class.forName("org.postgresql.Driver");
	    
	    //Chargement du fichier Props
	    Properties prop = new Properties();
	    try {
		prop.load(new FileInputStream(getServletContext().getRealPath("/Props.txt")));
	    url = prop.getProperty("url");
	    nom = prop.getProperty("nom");
		mdp = prop.getProperty("mdp");
	    } catch (Exception e) {
		out.println(e.getMessage());
	    }
	    
	    //Connexion a la base
	    con = DriverManager.getConnection(url,nom,mdp);
	    stmt = con.createStatement();

		rs = stmt.executeQuery("SELECT prix,libelle,pno From produits WHERE prix=(select MAX(prix) FROM produits) limit 1;");

		int prix = 0;
		String libelle = "";
		int pno = 0;

		if (rs.next()){
			prix = Integer.parseInt(rs.getString("prix"));
			libelle = rs.getString("libelle");
			pno = Integer.parseInt(rs.getString("pno"));
		}

		out.println("<?xml version="1.0" encoding="UTF-8"?>
					<produit>
						<prix>"+prix+"</prix>
						<libelle>"+libelle+"</libelle>
						<pno>"+pno+"</pno>
					<produit>");

	    }catch(Exception e) {
	    out.println(e.getMessage());
	}	
	finally {
	    try {
		stmt.close();
		con.close();
	    }
	    catch(Exception e) {
		out.println(e.getMessage());
	    }
	}
}
}
