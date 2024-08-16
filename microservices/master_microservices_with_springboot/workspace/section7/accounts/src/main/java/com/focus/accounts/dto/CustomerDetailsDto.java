package com.focus.accounts.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information."
)
@Data
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer.",
            example = "Ian Vinueza"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of the customer.",
            example = "ianchin@gmail.com"
    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be valid value")
    private String email;

    @Schema(
            description = "Mobil Number of the customer.",
            example = "098678234"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            name = "accounts",
            description = "Account details of the customer."
    )
    private AccountsDto accountsDto;

    @Schema(
            name = "cards",
            description = "Cards details of the customer."
    )
    private CardsDto cardsDto;

    @Schema(
            name = "loans",
            description = "Loans details of the customer."
    )
    private LoansDto loansDto;
}