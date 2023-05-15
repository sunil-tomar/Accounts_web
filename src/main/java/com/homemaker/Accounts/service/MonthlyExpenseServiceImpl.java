package com.homemaker.Accounts.service;


import com.homemaker.Accounts.dto.MonthlyExpenseDto;
import com.homemaker.Accounts.entities.MonthlyExpense;
import com.homemaker.Accounts.repository.MonthlyExpenseRepo;
import com.homemaker.Accounts.serviceinterface.IMonthlyExpenseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;


import static com.homemaker.Accounts.utils.MonthlyExpansesContants.*;

@Service
public class MonthlyExpenseServiceImpl extends BaseService<MonthlyExpense>
 implements IMonthlyExpenseService {

    @Autowired
    MonthlyExpenseRepo monthlyExpenseRepo;

    public MonthlyExpenseServiceImpl(MonthlyExpenseRepo repository) {
        super(repository);
    }

@Override
public Map<String, Object> addOrUpdateEntity(MonthlyExpenseDto monthlyExpenseDto){
    MonthlyExpense monthlyExpense=new MonthlyExpense();
    BeanUtils.copyProperties(monthlyExpenseDto, monthlyExpense);
    //SAVING.
    monthlyExpense= save(monthlyExpense);
    //RESPONSE.
    Map<String, Object> resp= new HashMap<>();
    resp.put(STATUS, TRUE);
    resp.put(MESSAGE, REGISTERED_SUCCESSFULLY);
    resp.put(ENTITY,monthlyExpense);
return resp;
}


    @Override
    public Map<String, Object> addDummyDataList() throws Exception {
        List<String> userNameList = Arrays.asList("James", "Josephine", "Art", "Lenna", "Donette", "Simona", "Mitsue", "Leota", "Sage", "Kris", "Minna", "Abel", "Kiley", "Graciela", "Cammy", "Mattie", "Meaghan", "Gladys", "Yuki",
                "Fletcher", "Bette", "Veronika", "Willard", "Maryann", "Alisha", "Allene", "Chanel", "Ezekiel", "Willow", "Bernardo", "Ammie", "Francine", "Ernie", "Albina", "Alishia", "Solange", "Jose",
                "Rozella", "Valentine", "Kati", "Youlanda", "Dyan", "Roxane", "Lavera", "Erick", "Fatima", "Jina", "Kanisha", "Emerson", "Blair", "Brock", "Lorrie", "Sabra", "Marjory", "Karl", "Tonette",
                "Amber", "Shenika", "Delmy", "Deeanna", "Blondell", "Jamal", "Cecily", "Carmelina", "Maurine", "Tawna", "Penney", "Elly", "Ilene", "Vallie", "Kallie", "Johnetta", "Bobbye", "Micaela",
                "Tamar", "Moon", "Laurel", "Delisa", "Viva", "Elza", "Devorah", "Timothy", "Arlette", "Dominque", "Lettie", "Myra", "Stephaine", "Lai", "Stephen", "Tyra", "Tammara", "Cory", "Danica",
                "Wilda", "Elvera", "Carma", "Malinda", "Natalie", "Lisha", "Arlene", "Alease", "Louisa", "Angella", "Cyndy", "Rosio", "Celeste", "Twana", "Estrella", "Donte", "Tiffiny", "Edna", "Sue",
                "Jesusa", "Rolland", "Pamella", "Glory", "Shawna", "Brandon", "Scarlet", "Oretha", "Ty", "Xuan", "Lindsey", "Devora", "Herman", "Rory", "Talia", "Van", "Lucina", "Bok", "Rolande", "Howard",
                "Kimbery", "Thurman", "Becky", "Beatriz", "Marti", "Nieves", "Leatha", "Valentin", "Melissa", "Sheridan", "Bulah", "Audra", "Daren", "Fernanda", "Gearldine", "Chau", "Theola", "Cheryl",
                "Laticia", "Carissa", "Lezlie", "Ozell", "Arminda", "Reita", "Yolando", "Lizette", "Gregoria", "Carin", "Chantell", "Dierdre", "Larae", "Latrice", "Kerry", "Dorthy", "Fannie", "Evangelina",
                "Novella", "Clay", "Jennifer", "Irma", "Eun", "Sylvia", "Nana", "Layla", "Joesph", "Annabelle", "Stephaine", "Nelida");

       // Random random = new Random();
        final Random random = SecureRandom.getInstanceStrong();

    List<MonthlyExpense> monthlyExpenseList=new ArrayList<>();
        userNameList.forEach(u->{
            if (u.length()>3) {
                double amount = random.nextDouble(300);
                MonthlyExpense monthlyExpense = new MonthlyExpense();
                monthlyExpense.setAmount(amount);
                monthlyExpense.setPaidFor(u);
                monthlyExpenseList.add(monthlyExpense);
            }
        });
        //SAVING RECORDS.
        saveAll(monthlyExpenseList);
        return new HashMap<>() {{
            put(STATUS, TRUE);
            put(MESSAGE, "All record added successfully!");
        }};
    }


}
 