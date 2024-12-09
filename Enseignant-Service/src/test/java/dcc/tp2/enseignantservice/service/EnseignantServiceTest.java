package dcc.tp2.enseignantservice.service;
import dcc.tp2.enseignantservice.client.ChercheurRestFeign;
import dcc.tp2.enseignantservice.client.ProjetRestFeign;
import dcc.tp2.enseignantservice.entities.Enseignant;
import dcc.tp2.enseignantservice.repository.EnseignantRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
//on utilise des mocks  qui sont des faux  simuler le fonctionnement des vrais couches repository et restclient car nous voulons faire des tests independenats
@ExtendWith(MockitoExtension.class) //pour creer les mocks
class EnseignantServiceTest {

    @Mock
    private EnseignantRepository enseignantRepository;
    @Mock
    private ChercheurRestFeign chercheurRestFeign;
    @Mock
    private ProjetRestFeign projetRestFeign;
    @InjectMocks
    private EnseignantService enseignat_service;


    @Test
    void create_Enseignant() {
        Enseignant enseignant = new Enseignant(null,"assia","namji","AB1223","assia@mail.com","123","informatique","Enseignant");
        Enseignant enseignantSaved = new Enseignant(1L,"assia","najmi","AB1223","assia@mail.com","123","informatique","Enseignant");
        //action
        Mockito.when(enseignantRepository.save(enseignant)).thenReturn(enseignantSaved);

        Enseignant result = enseignat_service.Create_Enseignant(enseignant);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignantSaved);
    }

    @Test
    void Not_create_Enseignant() {
        Enseignant enseignant = new Enseignant(null,"assia","najmi","AB1223","assia@mail.com","123","informatique","Enseignant");
        Enseignant enseignantSaved = new Enseignant(1L,"assia","najmi","AB1223","assia@mail.com","123","informatique","Enseignant");
        //action
        Mockito.when(enseignantRepository.save(enseignant)).thenReturn(null);
        Enseignant result = enseignat_service.Create_Enseignant(enseignant);
        AssertionsForClassTypes.assertThat(result).isNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isNotEqualTo(enseignantSaved);
    }

    @Test
    void getAll_Enseignant() {
        List<Enseignant> enseignantList = List.of(
                new Enseignant(1L,"najmi","assia","AB1223","assia@mail.com","123","informatique","Enseignant"),
                new Enseignant(2L,"ahmamad","soukaina","CD1223","soukaina@mail.com","123","informatique","Enseignant")
        );
        Mockito.when(enseignantRepository.findAll()).thenReturn(enseignantList);
        List<Enseignant> result = enseignat_service.GetAll_Enseignant();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignantList);
    }

    @Test
    void get_EnseignantByID() {
        Long id = 1L;
        Enseignant enseignant = new Enseignant(1L,"fatima","aflous","AB1223","fatima@mail.com","123","informatique","Enseignant");

        Mockito.when(enseignantRepository.findById(id)).thenReturn(Optional.of(enseignant));

        Enseignant result = enseignat_service.Get_EnseignantByID(id);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignant);

    }

    @Test
    void Not_get_EnseignantByID() {
        Long id = 8L;
        Enseignant enseignant = new Enseignant(1L,"assia","najmi","la1223","najmi@mail.com","123","informatique","Enseignant");
        Mockito.when(enseignantRepository.findById(id)).thenReturn(Optional.empty());
        Enseignant result = enseignat_service.Get_EnseignantByID(id);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isNotEqualTo(enseignant);

    }

    @Test
    void findByEmail() {
        String email = "fatima@mail.com";
        Enseignant enseignant = new Enseignant(1L,"fatima","aflous","AB1223","fatima@mail.com","123","informatique","Enseignant");

        Mockito.when(enseignantRepository.findEnseignantByEmail(email)).thenReturn(enseignant);

        Enseignant result = enseignat_service.FindByEmail(email);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignant);

    }

    @Test
    void NotfindByEmail() {
        String email = "assia@mail.com";
        Enseignant enseignant = new Enseignant(1L,"assia","najmi","AB1223","assia@mail.com","123","informatique","Enseignant");
        Mockito.when(enseignantRepository.findEnseignantByEmail(email)).thenReturn(null);

        Enseignant result = enseignat_service.FindByEmail(email);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isNotEqualTo(enseignant);

    }

    @Test
    void update_Enseignant() {
        Long id = 1L;
        Enseignant enseignant = new Enseignant(1L,"fatima","aflous","AB1223","fatima@mail.com","123","informatique","Enseignant");
        Enseignant enseignant_modify = new Enseignant(1L,"fatima","najmi","AB1223","assia@mail.com","123","informatique","Enseignant");
        Mockito.when(enseignantRepository.findById(id)).thenReturn(Optional.of(enseignant));
        Mockito.when(enseignantRepository.save(enseignant)).thenReturn(enseignant_modify);

        Enseignant result = enseignat_service.Update_Enseignant(enseignant_modify,id);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(enseignant);
    }

    @Test
    void delete_Enseignant() {
        Long id = 1L;
        enseignat_service.Delete_Enseignant(id);
    }

    @Test
    void statistique() {
        Long id = 1L;
        Map<String, Long> Statistiques = new HashMap<>();
        Statistiques.put("nombre de chercheur",2L);
        Statistiques.put("nombre de projet",4L);

        Mockito.when(chercheurRestFeign.nb_chercheur_Enseignant(id)).thenReturn(Long.valueOf(2));
        Mockito.when(projetRestFeign.nb_Projet_Enseignant(id)).thenReturn(Long.valueOf(4));

        Map<String, Long> result = enseignat_service.statistique(id);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(Statistiques);

    }
    @Test
    void Notstatistique() {
        Long id = 1L;
        Map<String, Long> Statistiques = new HashMap<>();
        Statistiques.put("nombre de chercheur",2L);
        Statistiques.put("nombre de projet",4L);

        Mockito.when(chercheurRestFeign.nb_chercheur_Enseignant(id)).thenReturn(null);
        Mockito.when(projetRestFeign.nb_Projet_Enseignant(id)).thenReturn(null);

        Map<String, Long> result = enseignat_service.statistique(id);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isNotEqualTo(Statistiques);

    }

}
