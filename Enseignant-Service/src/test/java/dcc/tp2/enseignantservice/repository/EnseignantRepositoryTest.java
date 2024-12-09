package dcc.tp2.enseignantservice.repository;
        import dcc.tp2.enseignantservice.entities.Enseignant;
        import org.assertj.core.api.AssertionsForClassTypes;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
        import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
        import org.springframework.test.context.ActiveProfiles;


        import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //indique que lorsque on lance le test  demarre tous ce qui est  lieer au base de donnees
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //pour avoir une vraie base de donnees our eviter que spring cree un bd en memoire pour le test
@ActiveProfiles("test") //charger a configuration necessaire pour realiser le test
class EnseignantRepositoryTest {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @BeforeEach //avant de demarrer le test j'ajoute 2 elements dans a bd pour faire le test
    void setUp() {
        enseignantRepository.save(new Enseignant(null,"fatima","aflous","AB1223","fatima@mail.com","123","informatique","Enseignant"));
        enseignantRepository.save(new Enseignant(null,"sohaila","sipou","CD2244","sohaila@mail.com","123","informatique","Enseignant"));
    }


    @Test
    public void findEnseignantByEmail() {
        // Given
        String email = "fatima@mail.com";
        Enseignant enseignant = new Enseignant(null, "fatima", "aflous", "AB1223", "fatima@mail.com", "123", "informatique", "Enseignant");
        // When
        Enseignant result = enseignantRepository.findEnseignantByEmail(email);
        // Then
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().ignoringFields("id") ;// Ignorer le champ 'id' lors de la comparaison.isEqualTo(enseignant); // Comparer à l'objet attendu
    }


    @Test
    public void shouldNotfindEnseignantByEmail(){
        String email = "abc@mail.com";
        Enseignant result = enseignantRepository.findEnseignantByEmail(email);
        AssertionsForClassTypes.assertThat(result).isNull();
    }

}