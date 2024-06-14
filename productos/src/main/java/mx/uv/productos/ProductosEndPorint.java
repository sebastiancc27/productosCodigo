package mx.uv.productos;

import mx.uv.t4is.saludos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;
import java.util.ArrayList;

@Endpoint
public class ProductosEndPorint {

   ArrayList<RegistrarProductoRequest> productosArray = new ArrayList<>();
   ArrayList<HistorialProductosResponse.Producto> productosH = new ArrayList<>(); 
   
    @Autowired
    private IProductos iSaludadores;

    @PayloadRoot(localPart = "RegistrarProductoRequest",namespace = "t4is.uv.mx/saludos")
    @ResponsePayload
    public RegistrarProductoResponse registrarProducto ( @RequestPayload RegistrarProductoRequest peticion){
        RegistrarProductoResponse respuesta = new RegistrarProductoResponse();
        RegistrarProductoRequest producto = new RegistrarProductoRequest();
        HistorialProductosResponse.Producto product = new HistorialProductosResponse.Producto();
        
        Productores productores =new Productores();

        //product.setId(peticion.getId());    
        product.setNombre(peticion.getNombre());
        product.setCantidad(peticion.getCantidad());
        product.setPrecio(peticion.getPrecio());
        product.setDescripcion(peticion.getDescripcion());

        //producto.setId(peticion.getId());
        producto.setNombre(peticion.getNombre());
        producto.setCantidad(peticion.getCantidad());
        producto.setPrecio(peticion.getPrecio());
        producto.setDescripcion(peticion.getDescripcion());
        productosArray.add(producto);

        productores.setNombre(peticion.getNombre());
        productores.setCantidad(peticion.getCantidad());
        productores.setPrecio(peticion.getPrecio());
        productores.setDescripcion(peticion.getDescripcion());

        iSaludadores.save(productores);

        productosH.add(product);
        
        respuesta.setRegistrado("Registrado");
        System.out.println("Producto : "+ producto.getNombre()+ " Precio: "+producto.getPrecio());
        return respuesta;
    }

    @PayloadRoot(localPart = "ProductoDisponibleRequest", namespace = "t4is.uv.mx/saludos")
    @ResponsePayload
    public ProductoDisponibleResponse productoDisponible(@RequestPayload ProductoDisponibleRequest peticion){
        ProductoDisponibleResponse respuesta = new ProductoDisponibleResponse();
        Iterable<Productores> productoD = iSaludadores.findAll();
        int id=peticion.getIdproducto();
        System.out.println(id);
        for(Productores producto : productoD){
               if(producto.getId()==id) {
                respuesta.setNombreproducto(producto.getNombre());
                respuesta.setExistencias(producto.getCantidad());
               }
        }
        return respuesta;
    }

    @PayloadRoot(localPart = "HistorialProductosRequest", namespace = "t4is.uv.mx/saludos")
    @ResponsePayload
    public HistorialProductosResponse historialProductos() {
        HistorialProductosResponse respuesta = new HistorialProductosResponse();
        //!GUARDA TODO LOS ELEMENTOS DE LA BD
        Iterable<Productores> productores = iSaludadores.findAll();
        //! TIPO DE DATOS 
        //* NOMBRE DEL ITEM - > ALIAS DE CADA ITEM DEL ARRAY
        //?ARRAY O LISTA
        for (Productores productor : productores) {
            HistorialProductosResponse.Producto product = new HistorialProductosResponse.Producto();
            //ProductosRegistradosResponse.Producto product = new ProductosRegistradosResponse.Producto();
            product.setId(productor.getId());
            product.setNombre(productor.getNombre());
            product.setDescripcion(productor.getDescripcion());
            product.setPrecio(productor.getPrecio());
            product.setCantidad(productor.getCantidad());

            respuesta.getProducto().add(product);
        } //!Flowers O Girls like you ?
        return respuesta;
    }
    

}
