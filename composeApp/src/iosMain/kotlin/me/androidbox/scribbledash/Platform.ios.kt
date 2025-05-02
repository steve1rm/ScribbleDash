package me.androidbox.scribbledash

import androidx.compose.ui.graphics.Path
import me.androidbox.scribbledash.gamemode.presentation.utils.ParseXmlDrawable
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

class ParseXmlDrawableImp: NSObject, XMLParserDelegate {
    private var viewportWidth: CGFloat = 24.0
    private var viewportHeight: CGFloat = 24.0
    private var pathDataList: [String] = []
    private var currentElement: String = ""
    private let TAG = "VectorParse"

    func parse(drawableName: String) -> [UIBezierPath] {
        guard let url = Bundle.main.url(forResource: drawableName, withExtension: "xml"),
              let parser = XMLParser(contentsOf: url) else {
            print("\(TAG): Drawable resource not found: \(drawableName)")
            return []
        }

        parser.delegate = self
        if !parser.parse() {
            print("\(TAG): XML parsing error: \(parser.parserError?.localizedDescription ?? "Unknown error")")
            return []
        }

        print("\(TAG): Parsing finished. Found \(pathDataList.count) paths.")

        let paths = pathDataList.compactMap { pathData -> UIBezierPath? in
            return UIBezierPath(svgPath: pathData)
        }

        return paths
    }

    // MARK: - XMLParserDelegate methods

    func parser(_ parser: XMLParser, didStartElement elementName: String, namespaceURI: String?, qualifiedName qName: String?, attributes attributeDict: [String : String]) {
        currentElement(elementName: elementName, attributes: attributeDict)
    }

    private func parser(_ parser: XMLParser, didStartElement elementName: String,
                        namespaceURI: String?, qualifiedName qName: String?,
                        attributes attributeDict: [String : String] = [:]) {
        currentEvent(elementName: elementName, attributes: attributeDict)
    }

    private func currentEvent(elementName: String, attributes: [String: String]) {
        switch elementName {
        case "vector":
            if let vwStr = attributes["android:viewportWidth"],
               let vhStr = attributes["viewportHeight"],
               let vw = Float(vwStr), let vh = Float(attributes["viewportHeight"] ?? "") {
                print("\(TAG): Found <vector>: viewportWidth=\(vwStr), viewportHeight=\(vhStr)")
            }
        case "path":
            if let pathData = attributes["pathData"], !pathData.isEmpty {
                pathDataList.append(pathData)
            } else {
                print("\(TAG): Found <path> tag with null or blank pathData.")
            }
        default:
            break
        }
    }
}

To support SVG path parsing, you may need a helper extension for `UIBezierPath`. Here's a simple example using a third-party library like PocketSVG or similar:

```swift

extension UIBezierPath {
    convenience init?(svgPath: String) {
        guard let path = try? SVGBezierPath.path(fromSVGString: svgPath) else {
            return nil
        }
        self.init(cgPath: path.cgPath)
    }
}


actual class ParseXmlDrawableImp : ParseXmlDrawable {
    actual override fun parser(drawableName: String): List<Path> {
        TODO("Not yet implemented")
    }
}