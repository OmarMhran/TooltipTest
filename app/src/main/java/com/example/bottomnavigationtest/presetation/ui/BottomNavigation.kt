import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup

@Composable
fun BottomNavigation() {
    var selectedIndex by remember { mutableStateOf(0) }
    var tooltipText by remember { mutableStateOf("Home selected. This is a helpful tooltip for the Home section.") }

    // Updated nav items with icons
    val navItems = listOf(
        "Home" to Icons.Default.Home,
        "Connect" to Icons.Default.Link,
        "Question" to Icons.Default.QuestionMark,
        "Tools" to Icons.Default.Build,
        "Profile" to Icons.Default.Person
    )

    val density = LocalDensity.current

    fun tooltipWithPointerShape(pointerOffset: Float): Shape = GenericShape { size, _ ->
        with(density) {
            val cornerRadius = 16.dp.toPx()
            val pointerWidth = 16.dp.toPx()
            val pointerHeight = 8.dp.toPx()
            val halfPointerWidth = pointerWidth / 2

            // Shape drawing logic
            moveTo(cornerRadius, 0f)
            lineTo(size.width - cornerRadius, 0f)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    left = size.width - 2 * cornerRadius,
                    top = 0f,
                    right = size.width,
                    bottom = 2 * cornerRadius
                ),
                startAngleDegrees = -90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(size.width, size.height - pointerHeight - cornerRadius)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    left = size.width - 2 * cornerRadius,
                    top = size.height - pointerHeight - 2 * cornerRadius,
                    right = size.width,
                    bottom = size.height - pointerHeight
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(pointerOffset + halfPointerWidth, size.height - pointerHeight)
            lineTo(pointerOffset, size.height)
            lineTo(pointerOffset - halfPointerWidth, size.height - pointerHeight)
            lineTo(cornerRadius, size.height - pointerHeight)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    left = 0f,
                    top = size.height - pointerHeight - 2 * cornerRadius,
                    right = 2 * cornerRadius,
                    bottom = size.height - pointerHeight
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, cornerRadius)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(
                    left = 0f,
                    top = 0f,
                    right = 2 * cornerRadius,
                    bottom = 2 * cornerRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
        }
        close()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content background, hidden when tooltip is visible
        if (tooltipText.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                // Main content of your screen
            }
        }

        // Overlay to block background interactions
        if (tooltipText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(enabled = false) {} // Blocks interactions with background
            )
        }

        // Bottom navigation bar with custom height and color
        BottomNavigation(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(70.dp), // Increase height of BottomNavigation
            backgroundColor = Color.White // Set background color to white
        ) {
            navItems.forEachIndexed { index, (label, icon) ->
                BottomNavigationItem(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        tooltipText = "$label selected. This is a helpful tooltip for the $label section."
                    },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = Color.Blue // Set icon color to cyan
                        )
                    },
                    label = {
                        Text(label, color = Color.Blue, fontSize = 12.sp) // Set title color to cyan
                    }
                )
                }


            }
        }

        // Tooltip (Popup)
        tooltipText?.let { text ->

           if (tooltipText.isNotEmpty()){
               Popup(
                   alignment = Alignment.BottomCenter,
                   offset = IntOffset(x = 0, y = -250) // Large margin above the navigation
               ) {
                   // Calculate the horizontal position for the pointer based on the selected button
                   val screenWidth = with(density) { 360.dp.toPx() } // Adjust to the actual screen width if needed
                   val buttonWidth = screenWidth / navItems.size
                   val pointerOffset = (buttonWidth * selectedIndex) + (buttonWidth / 2)

                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 16.dp) // Margin on the sides of the screen
                           .background(
                               Color.White,
                               tooltipWithPointerShape(pointerOffset)
                           ) // Dynamic pointer position
                           .padding(horizontal = 16.dp, vertical = 16.dp)
                           .clickable { tooltipText = "" } // Hide tooltip when clicked
                   ) {
                       Text(
                           text = text,
                           color = Color.Black,
                           fontSize = 18.sp
                       )
                   }
               }
           }
        }
    }

