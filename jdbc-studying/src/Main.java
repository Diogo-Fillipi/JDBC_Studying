public class Main {
    public static void main(String[] args){
        PersonDTO personDTO = new PersonDTO();
        PersonDAO personDAO = new PersonDAO();
        personDTO.setTaskId(2);
        personDTO.setName("Limpar o quarto");
        personDTO.setDueDate("22/10/2022");
        personDAO.insert(personDTO);

    }
}
