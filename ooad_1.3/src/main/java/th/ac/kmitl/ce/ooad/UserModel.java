package th.ac.kmitl.ce.ooad;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
/**
 * Created by Nut on 10/12/2015.
 */
public class UserModel {
    private static UserModel user_controller = new UserModel();
    @Autowired private static AccountRepository accountRepository;
    private UserModel(){
    }

    public static UserModel getInstance(){
        return user_controller;
    }

    protected static boolean addUser(String username, String password, String name, String email, String imgLoc){
        Profile profile = new Profile(email, name, imgLoc);
        Account account = new Account(profile, username, password, UUID.randomUUID().toString());
        try {
            Account temp = accountRepository.findByUsername(username);
            System.out.println("Found user: " + temp.getUsername());
            return false;
        }
        catch (Exception e){
            accountRepository.save(account);
            return true;
        }
    }

    protected static boolean isExist(String username){
        if(accountRepository.findByUsername(username) != null) return true;
        return false;
    }

    protected static boolean authenUser(String username, String password){
        if(isExist(username)){
            String tmp_pwd = accountRepository.findByUsername(username).getPassword();
            if(password.equals(tmp_pwd)) return true;
        }
        return false;
    }

    protected static boolean updatePwd(String username, String password){
        if(authenUser(username, password)) {
            Account temp = accountRepository.findByUsername(username);
            temp.setPassword(password);
            accountRepository.save(temp);
            return true;
        }
        return false;
    }

    protected static boolean updateName(String username, String newName){
        if(isExist(username)) {
            Account temp = accountRepository.findByUsername(username);
            Profile pro_temp = temp.getProfile();
            pro_temp.setName(newName);
            temp.setProfile(pro_temp);
            accountRepository.save(temp);
            return true;
        }
        return false;
    }

    private static String getUserById(String userId){
        return accountRepository.findByUserId(userId).getUsername();
    }

    protected static Account getAccountById(String userId){
        return accountRepository.findByUserId(userId);
    }

    protected static boolean addCloudAccount(String userId, String password, int cloudProv, String cloudUsername, String cloudPassword){

        if(authenUser(getUserById(userId), password)){
            CloudProvider cloudProvider;
            switch (cloudProv){
                case 0 : cloudProvider = CloudProvider.GOOGLE; break;
                case 1 : cloudProvider = CloudProvider.AMAZON; break;
                case 2 : cloudProvider = CloudProvider.AZURE; break;
                case 3 : cloudProvider = CloudProvider.DIGITAL_OCEAN; break;
                case 4 : cloudProvider = CloudProvider.VMWARE; break;
                default : cloudProvider = CloudProvider.UNKNOWN; break;
            }
            CloudAccount temp = new CloudAccount(cloudProvider, getUserById(userId), password);
            getAccountById(userId).addCloud(temp);
        }
        return false;
    }
}
