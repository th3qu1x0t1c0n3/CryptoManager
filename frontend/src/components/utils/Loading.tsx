function Loading() {
    return (
        <div className="h-screen flex justify-center pt-10" data-testid="loading-component">
            <div className="animate-spin h-12 w-12 border-t-4 border-port-gray rounded-full border-opacity-25"></div>
        </div>
    )
}

export default Loading;