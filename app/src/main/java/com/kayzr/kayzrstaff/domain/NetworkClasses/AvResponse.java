package com.kayzr.kayzrstaff.domain.NetworkClasses;


import java.util.List;

public class AvResponse extends Response {

    private List<NetworkAvailability> data;

    public List<NetworkAvailability> getData() {
        return data;
    }

    public void setData(List<NetworkAvailability> data) {
        this.data = data;
    }

    public class NetworkAvailability {
        private String username;
        private String userId;
        private List<String> tournaments;

        public NetworkAvailability(String username, String userId, List<String> tournaments) {
            this.username = username;
            this.userId = userId;
            this.tournaments = tournaments;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<String> getTournaments() {
            return tournaments;
        }

        public void setTournaments(List<String> tournaments) {
            this.tournaments = tournaments;
        }
    }
}
