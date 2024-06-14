package mx.uv.productos;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface IProductos extends CrudRepository<Productores,Integer>{
    List<Productores> findByNombre(String nombre);
}
