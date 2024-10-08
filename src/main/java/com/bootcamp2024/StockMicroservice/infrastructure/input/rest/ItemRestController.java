package com.bootcamp2024.StockMicroservice.infrastructure.input.rest;


import com.bootcamp2024.StockMicroservice.application.dto.request.AddItem;
import com.bootcamp2024.StockMicroservice.application.dto.response.ItemResponse;
import com.bootcamp2024.StockMicroservice.application.dto.response.PaginationResponse;
import com.bootcamp2024.StockMicroservice.application.handler.IItemHandler;
import com.bootcamp2024.StockMicroservice.infrastructure.configuration.exceptionHandler.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item/")
@RequiredArgsConstructor
public class ItemRestController {

    private final IItemHandler itemHandler;

    @Operation(summary = "Add a new Item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddItem.class))),
            @ApiResponse(responseCode = "409", description = "Item already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Void> createItem(@RequestBody @Valid AddItem addItem){
        itemHandler.createItem(addItem);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get a Item by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping("/name/{itemName}")
    public ResponseEntity<ItemResponse> getItemByName(@PathVariable String itemName){
        return ResponseEntity.ok(itemHandler.findByName(itemName));
    }

    @Operation(summary = "Get a Item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping("/id/{itemId}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long itemId){
        return ResponseEntity.ok(itemHandler.findById(itemId));
    }

    @Operation(summary = "Get All Items by pagination")
    @Parameters(value = {
            @Parameter(in = ParameterIn.QUERY,name = "page", description = "Page number to be returned"),
            @Parameter(in = ParameterIn.QUERY,name = "size", description = "Number of elements per page"),
            @Parameter(in = ParameterIn.QUERY, name = "ord", description = "Determines if the results should be ordered ascending(True) or descending(False)")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Items returned",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<ItemResponse>> getAllItems(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam int size,
            @RequestParam String sortParam,
            @RequestParam(value = "ord", defaultValue = "true") boolean ord){
        return ResponseEntity.ok(itemHandler.getAllItems(page, size, sortParam, ord));
    }

}
