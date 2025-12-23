import UIKit
import SwiftUI

struct NavigationView: View {
    var body: some View {
        TabView {
            HomeView()
                .tabItem { Label("Home", systemImage: "house") }

            FavoritesView()
                .tabItem { Label("Favorites", systemImage: "heart") }

            NotificationsView()
                .tabItem { Label("Notifications", systemImage: "bell") }

            ProfileView()
                .tabItem { Label("Profile", systemImage: "person") }
        }
        .toolbarBackground(.ultraThinMaterial, for: .tabBar)
               .toolbarBackground(.visible, for: .tabBar)
    }
}
