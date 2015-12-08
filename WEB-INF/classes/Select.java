import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.Properties;

@WebServlet("/servlet/select")
public class Select extends HttpServlet
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

		rs = stmt.executeQuery("SELECT count(*) as count From produits;");

		int result=0;
		if (rs.next()){result = Integer.parseInt(rs.getString("count"));}
		out.println("Nombre de lignes dans produits : " + result);

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
