import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
public class PersonDAO {
    public void insert(PersonDTO personDTO){
        Connection connection = DbStudying.getConnection();
        String SQL = "INSERT INTO Tasks (namee, dueDate) VALUES (?, ?)";
        System.out.println("Number of tasks with task_id: " + personDTO.getTaskId() + " is " + checkIfExists(personDTO));

        if(checkIfExists(personDTO) == 0){
            try{
                PreparedStatement PS = connection.prepareStatement(SQL);
                PS.setString(1, personDTO.getName());
                PS.setString(2, personDTO.getDueDate());
                PS.execute();
                PS.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Task already exist in the database");
        }

    }

    public void delete(int idTask) throws SQLException{
        Connection connection = DbStudying.getConnection();
        String SQL = "DELETE FROM Tasks WHERE task_id = ?";
        try(PreparedStatement PS = connection.prepareStatement(SQL)){
            PS.setInt(1, idTask);
            PS.executeUpdate();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public List<PersonDTO> toListAll() throws SQLException{
        List<PersonDTO> listTask = new ArrayList<PersonDTO>();
        Connection connection = DbStudying.getConnection();
        String SQL = "SELECT * FROM Tasks";
        try(PreparedStatement PS = connection.prepareStatement(SQL)){
            ResultSet resultSet= PS.executeQuery();

            while(resultSet.next()){
                PersonDTO personDTO = new PersonDTO();
                personDTO.setTaskId(resultSet.getInt("task_id"));
                personDTO.setName(resultSet.getString("namee"));
                personDTO.setDueDate(resultSet.getString("dueDate"));

                listTask.add(personDTO);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listTask;
    }

    public void update(PersonDTO personDTO) throws SQLException{
        Connection connection = DbStudying.getConnection();
        String SQL = "UPDATE Tasks SET name = ?, dueDate = ? WHERE task_id = ?";
        try(PreparedStatement PS = connection.prepareStatement(SQL)){

            PS.setString(1, personDTO.getName());
            PS.setString(2, personDTO.getDueDate());
            PS.setInt(3, personDTO.getTaskId());
            PS.execute();
            PS.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int checkIfExists(PersonDTO personDTO){
        Connection connection = DbStudying.getConnection();
        String SQL = "SELECT COUNT(*) FROM Tasks WHERE task_id = ?";
        try(PreparedStatement PS = connection.prepareStatement(SQL)){

            PS.setInt(1, personDTO.getTaskId());
            ResultSet resultSet = PS.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count;
        }catch(SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    /* private boolean checkIfExists(PersonDTO personDTO) throws SQLException {
        String checkIfExistsSQL = "SELECT COUNT(*) FROM Tasks WHERE namee = ?";
        try (PreparedStatement checkIfExistsPS = DbStudying.getConnection().prepareStatement(checkIfExistsSQL)) {
            checkIfExistsPS.setInt(1, personDTO.getTaskId());
            ResultSet resultSet = checkIfExistsPS.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);


            return count > 0; // If the count is greater than 0, the task is in the database already.
        }
    }*/

}
