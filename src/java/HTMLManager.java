public class HTMLManager {

    public String convertTableToHTML( Table table ){
        StringBuilder htmlCode = new StringBuilder();

        htmlCode.append( "<table border=1>" );

        boolean isHeader = true;
        
        for( String[] row : table.getMatrix() ){
            htmlCode.append("<tr>");

            for( String cell : row ){
                if( isHeader ){
                    htmlCode.append("<td>"+cell+"</td");
                    isHeader = false;
                }else{
                    htmlCode.append("<td>"+cell+"</td>");
                }
                
            }
            htmlCode.append("</tr>");
        }
        htmlCode.append("</table>");

        return htmlCode.toString();
    }
}
