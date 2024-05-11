/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'port-dark': 'var(--port-dark-color)',
        'port-blue': 'var(--port-blue-color)',
        'port-yellow': 'var(--port-yellow-color)',
        'port-gray': 'var(--port-gray-color)',
        'port-white': 'var(--port-white-color)',
      },
    },
  },
  plugins: [],
}