package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T>{   // Svaki repozitori implementira tipiziranu verziju ovog interfejsa. 
    void dodaj(T zaDodavanje) throws SQLException;
    T getJedan(T trazeni) throws SQLException;
    List<T> getSve() throws SQLException;
    void izmeni(T stariT, T noviT) throws SQLException;
    void izbrisi(T zaBrisanje) throws SQLException;
}
