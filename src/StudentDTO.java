public record StudentDTO(int id, String name, String role) {

    public StudentDTO{
        if(id <= 0){
            throw new IllegalArgumentException("ID must be greater than 0");
        } if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
