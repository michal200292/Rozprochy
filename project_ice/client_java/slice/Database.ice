
module Database
{
    interface UserInfo
    {
        string getName();
        int getPhoneNumber();
    }

    interface UpdateInfo
    {
        string authenticate(string password);
        string logOut();
        string changeName(string newName);
        string changeNumber(int newNumber);
    }
}
