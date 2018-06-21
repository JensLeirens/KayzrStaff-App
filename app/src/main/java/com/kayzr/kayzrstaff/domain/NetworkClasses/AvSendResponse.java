package com.kayzr.kayzrstaff.domain.NetworkClasses;

public class AvSendResponse extends Response {
    private AvSendResponseData data;

    public AvSendResponseData getData() {
        return data;
    }

    public void setData(AvSendResponseData data) {
        this.data = data;
    }

    public class AvSendResponseData {
        private int success;
        private int failed;

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int succes) {
            this.success = succes;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }
    }
}

