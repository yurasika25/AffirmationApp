import SwiftUI
import ComposeApp

struct HomeView: View {

    @StateObject private var vm = HomeViewModel()

    var body: some View {
        NavigationStack {
            Text("Home")
                .navigationTitle("Home")
        }
    }
}