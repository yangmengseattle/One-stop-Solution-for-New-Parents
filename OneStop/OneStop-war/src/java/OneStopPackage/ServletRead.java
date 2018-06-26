/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OneStopPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persist.Product;
import persist.ProductFacadeLocal;

/**
 *
 * @author yangm
 */
public class ServletRead extends HttpServlet {
    @EJB
    private ProductFacadeLocal productFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            List<Product> pl = productFacade.findAll();//get whatever product is in database   
            List<String> names = new ArrayList<String>();
            List<String> nameSorted = new ArrayList<String>();;//the sorted List of name received
            //put the names of the products into a list
            for(Product p : pl){
                names.add(p.getName());
            }    
            
            try {
                //find the OneStopServerObj
                OneStopServerInterface OneStopServerObj = (OneStopServerInterface) Naming.lookup( "rmi://localhost:15001/server" );
                System.out.println("OneStopServerObj found!");
                nameSorted = OneStopServerObj.sorting(names);//get the sorted namelist by calling remote methods
                sortProduct(nameSorted, pl);
            } catch (NotBoundException ex) {
                Logger.getLogger(ServletRead.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ServletRead.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ServletRead.class.getName()).log(Level.SEVERE, null, ex);
            }            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletRead</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletRead at " + request.getContextPath() + "</h1>");
            for(Product p : pl){             
                out.println( "Product ID: " +  p.getId() + "<br>" + " Product name: " + p.getName() + "<br>");
                out.println(" Product description: " +  p.getDescription() + "<br>");
                out.println( " Product rate: " +  p.getRate() + "<br>");
                out.println(String.format("<img src = %s alt = 'image'/><br>", p.getpImage()));  
                out.println(String.format("<a href = %s>Click here to buy!</a><br>", p.getpURL()));  
                out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------<br>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    //This method sorts the list of products 
    public void sortProduct(List<String> nameSorted, List<Product> lProduct){
        HashMap<String, Product> container = new HashMap<String, Product>();
        //fill the container with name:product pairs 
        for(Product p : lProduct){
            container.put(p.getName(), p);
        }
        lProduct.clear();//clear the Product list
        for(String name : nameSorted){
            if(container.containsKey(name)){
                lProduct.add(container.get(name));
            }
        }      
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
