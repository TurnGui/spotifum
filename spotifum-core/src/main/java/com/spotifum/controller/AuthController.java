package com.spotifum.controller;

import com.spotifum.model.SubscriptionPlan;
import com.spotifum.model.User;
import com.spotifum.model.UserRepository;
import com.spotifum.view.AuthView;
import com.spotifum.view.AuthView.SignUpDetails;

public final class AuthController {
    private AuthController() {
    }

    public static void login() {
        String email = AuthView.promptLogin();
        if (email == null) {
            return;
        }

        User user = UserRepository.getInstance().findByEmail(email).orElse(null);
        if (user == null) {
            AuthView.showUserNotFound();
            return;
        }

        UserMenuController.run(user);
    }

    public static void signUp() {
        SignUpDetails details = AuthView.promptSignUp();
        if (details == null) {
            return;
        }

        if (UserRepository.getInstance().exists(details.email())) {
            AuthView.showEmailTaken();
            return;
        }

        SubscriptionPlan plan = AuthView.promptPlanSelection();
        User user = new User(details.name(), details.email(), details.address(), plan);
        UserRepository.getInstance().add(user);
        PersistenceController.saveUsers();

        UserMenuController.run(user);
    }
}
