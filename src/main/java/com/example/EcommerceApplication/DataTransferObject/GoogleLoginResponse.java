package com.example.EcommerceApplication.DataTransferObject;

public class GoogleLoginResponse {
    private String jwtToken;
    private String email;
    private String role;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public GoogleLoginResponse(String jwtToken, String email, String role) {
        this.jwtToken = jwtToken;
        this.email = email;
        this.role = role;
    }
}
