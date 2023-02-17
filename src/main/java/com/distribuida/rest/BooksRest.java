package com.distribuida.rest;

import com.distribuida.clientes.authors.AuthorRestProxy;
import com.distribuida.clientes.authors.AuthorsCliente;
import com.distribuida.db.Books;
import com.distribuida.dto.BooksDto;
import com.distribuida.servicios.BooksRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
        info = @Info(
                title = "API BooksRest",
                version = "1.0.0"
        )
)
public class BooksRest {
    @Inject
    BooksRepository bookService;
    @RestClient
    @Inject
    AuthorRestProxy proxyAuthor;


    @GET
    @Path("/{id}")
    @Operation(summary = "Retorna un books dependiendo del id",
            description = "Books con 5 variables {id, isbn, title, author, price}")
    @APIResponse(description = "JSON simple que contiene a los books",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Books.class)))
    public Response findById(@PathParam("id") Integer id) {
        Optional<Books> ret = bookService.findById(id);

        if (ret.isPresent()) {
            return Response.ok(ret.get()).build();
        } else {
            String msg = String.format("Book[id=%d] not found.", id);

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(msg)
                    .build();
        }
    }

    @GET
    @Operation(summary = "Retorna una lista de los books ",
            description = "Lista de Book ")
    @APIResponse(description = "JSON simple que contiene a los books",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Books.class)))
    public List<Books> findAll() {
        System.out.println("Buscando todos");
        return bookService.findAll();
    }

    @GET
    @Operation(summary = "Retorna una lista de los books y author",
            description = "Lista de Book incluyendo la lista de author ")
    @APIResponse(description = "JSON simple que contiene a los books",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Books.class)))
    @Path("/all")
    public List<BooksDto> findAllCompleto() throws Exception {
        var books = bookService.findAll();
        List<BooksDto> ret = books.stream()
                .map(s -> {
                    System.out.println("*****buscando " + s.getId());

                    AuthorsCliente author = proxyAuthor.findById(s.getId().longValue());
                    return new BooksDto(
                            s.getId(),
                            s.getIsbn(),
                            s.getTitle(),
                            s.getAuthor(),
                            s.getPrice(),
                            String.format("%s, %s", author.getLastName(), author.getFirstName())
                    );
                })
                .collect(Collectors.toList());

        return ret;
    }

    @POST
    @Operation(summary = "Metodo para guardar",
            description = "Se necesita un json con las variables ")
    @APIResponse(description = "JSON simple ",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Books.class)))
    public void insert(Books books) {
        bookService.insert(books);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Metodo para actualizar",
            description = "Se necesita un json con las variables ")
    @APIResponse(description = "JSON simple ",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Books.class)))
    public void update(Books books, @PathParam("id") Integer id) {
        books.setId(id);

        bookService.update(books);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Metodo para eliminar",
            description = "Necesita el {id} para eliminar un books de la base de datos")
    @APIResponse(description = "JSON simple ",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Books.class)))
    public void delete(@PathParam("id") Integer id) {
        bookService.delete(id);
    }


}
