import UIKit
import SwiftUI
import ComposeApp


struct ComposeView: UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewController {
        let vc = MainViewControllerKt.MainViewController()
        vc.view.backgroundColor = UIColor(red: 0xEF/255, green: 0xF3/255, blue: 0xFF/255, alpha: 1.0)

        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}


struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all)
    }
}
