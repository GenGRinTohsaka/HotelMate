    package com.losbraulios.hotelmate.DTO;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import lombok.Data;

    @Data
    public class UserSaveDTO {

        private Long idUser;
        @NotBlank(message = "El nombre de usuario no puede estar vacio")
        private String nameUser;
        @Email(message = "El formato de correo no es el correcto")
        @NotBlank(message = "El correo no puede ir vacio")
        private String emailUser;
        @NotBlank (message = "La contraseña no puede estar vacia")
        private String passwordUser;
        
    }
