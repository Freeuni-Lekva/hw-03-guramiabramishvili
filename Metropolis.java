
import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class Metropolis extends AbstractTableModel{

    private static final String[] colNames = {"Metropolis","Continent","Population"};

    private ResultSet rs;
    private Connection conn;

    public Metropolis(){
        conn = MetropolisDB.getConnection();
    }

    @Override
    public int getRowCount() {
        int count = 0;
        if (rs != null) {
            try {
                if (rs.last()) {

                    count = rs.getRow();
                    rs.beforeFirst();

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public void search(String metropolis, String continent, String population, boolean pop, boolean match){
        try{
            StringBuilder str = new StringBuilder();
            str.append("SELECT * FROM metropolises");

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            statement.executeQuery("USE " + MetropolisDB.database + " ");

            boolean auxCondition = false;

            if(metropolis.isEmpty() && continent.isEmpty() && population.isEmpty()){
                rs = statement.executeQuery(str.toString());
                fireTableDataChanged();
                return;
            }
            if(match) {
                if (!metropolis.isEmpty()) {
                    str.append(" WHERE metropolis = \"" + metropolis + "\"");
                    auxCondition = true;
                }
                if (!continent.isEmpty()) {
                    if (auxCondition) str.append(" AND continent = \"" + continent + "\"");
                    else {
                        str.append(" WHERE continent = \"" + continent + "\"");
                        auxCondition = true;
                    }
                }
                if (!population.isEmpty()) {
                    if (pop) {
                        if (auxCondition)
                            str.append(" AND population >= \"" + Long.parseLong(population) + "\"");
                        else {
                            str.append(" WHERE population >= \"" + Long.parseLong(population) + "\"");
                        }
                    } else {
                        if (auxCondition)
                            str.append(" AND population <= \"" + Long.parseLong(population) + "\"");
                        else {
                            str.append(" WHERE population <= \"" + Long.parseLong(population) + "\"");
                        }
                    }
                }
            } else {
                if (!metropolis.isEmpty()){
                    str.append(" WHERE metropolis like \"%"+ metropolis +  "%\"");
                    if(!continent.isEmpty()){
                        str.append(" AND continent like \"%" + continent + "%\"");
                    }
                    if(!population.isEmpty()){
                       if(pop){
                           str.append(" AND population >= " + population);
                       }else{
                           str.append(" AND population <= " + population);
                       }
                    }
                }else{
                    if(!continent.isEmpty()){
                        str.append(" WHERE continent like \"%" + continent +  "%\"");
                        if(pop){
                            str.append(" AND population >= " + population);
                        }else{
                            str.append(" AND population <= " + population);
                        }
                    }
                }
            }
            rs = statement.executeQuery(str.toString());
            fireTableDataChanged();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(String metropolis, String continent, String population){
        try{
            PreparedStatement statement = conn.prepareStatement("USE " + MetropolisDB.database + " ");
            statement.executeQuery();
            if(metropolis.isEmpty() || continent.isEmpty() || population.isEmpty()){
                System.out.println("Empty fields");
            }else{
                statement = conn.prepareStatement("INSERT INTO METROPOLISES VALUES (\"" + metropolis + "\",\"" + continent + "\"," + population + ")");
                statement.executeUpdate();
                search(metropolis, continent, population, false, true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public Object getValueAt(int row, int col) {
        Object val = null;
        try {
            if (rs != null) {
                rs.absolute(row + 1);
                val =  rs.getObject(col + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    @Override
    public String getColumnName(int i) {
        return colNames[i];
    }

}

