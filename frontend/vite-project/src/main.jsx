import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { ChakraProvider } from '@chakra-ui/react'

import { createStandaloneToast } from '@chakra-ui/react'
import App from "./App.jsx";

const { ToastContainer } = createStandaloneToast()

ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <App />
                <ToastContainer></ToastContainer>
            </ChakraProvider>
        </React.StrictMode>,
)
