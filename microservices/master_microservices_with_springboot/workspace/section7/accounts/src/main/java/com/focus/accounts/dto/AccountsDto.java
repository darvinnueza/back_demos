package com.focus.accounts.dto;

import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information."
)
@Data
public class AccountsDto {

    @Schema(
            description = "Account Number of EazyBank account.",
            example = "1476862942"
    )
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp = "(^$[0-9]{10})", message = "Account Number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account Type of EazyBank account.",
            example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(
            description = "EazyBank branch address.",
            example = "124 Main Street, New York"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}